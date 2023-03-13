package request.sender.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import request.sender.dto.RequestDto;
import request.sender.util.RequestSender;
import request.sender.util.factory.EntityClassFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@RequiredArgsConstructor
public class RequestSendingServiceImpl implements RequestSendingService {
    private final List<CompletableFuture<Object>> futures = new LinkedList<>();
    private final RestTemplate restTemplate;
    private final EntityClassFactory entityClassFactory;
    @Value("${threads.core.number}")
    private int coreThreadsNumber;
    @Value("${threads.upper.number}")
    private int upperThreadsNumber;

    @Value("${thread.keepAlive.time}")
    private int keepAliveTime;

    public void startRequestSending(RequestDto requestDto) {
        Executor executor = getTasksExecutor(requestDto.requestsNumber());
        for (int i = 0; i < requestDto.requestsNumber(); i++) {
            var future = CompletableFuture.supplyAsync(new RequestSender(restTemplate, requestDto.endpoint(), requestDto.entityName(), entityClassFactory),
                    executor);
            futures.add(future);
        }
    }

    private Executor getTasksExecutor(int requestsNumber) {
        return new ThreadPoolExecutor(
                Math.min(requestsNumber, coreThreadsNumber),
                Math.max(requestsNumber, upperThreadsNumber),
                keepAliveTime,
                SECONDS,
                new ArrayBlockingQueue<>(requestsNumber),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public List<Object> getResults() {
        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
}
