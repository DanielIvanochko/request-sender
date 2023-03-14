package request.sender.service;

import request.sender.dto.RequestDto;

import java.util.List;

public interface RequestSendingService {
    void startRequestSending(RequestDto requestDto);

    List<Object> getResults();
}
