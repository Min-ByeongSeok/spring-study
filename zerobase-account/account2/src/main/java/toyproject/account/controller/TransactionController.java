package toyproject.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toyproject.account.dto.CancelBalance;
import toyproject.account.dto.TransactionDto;
import toyproject.account.dto.UseBalance;
import toyproject.account.exception.AccountException;
import toyproject.account.service.TransactionService;

import javax.validation.Valid;

/**
 * 잔액 관련 컨트롤러
 * 1. 잔액 사용
 * 2. 잔액 사용 취소
 * 3. 거래 확인
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transaction/use")
    public UseBalance.Response useBalance(
            @Valid @RequestBody UseBalance.Request request
    ) {
        TransactionDto transactionDto
                = transactionService.useBalance(request.getUserId(), request.getAccountNumber(), request.getAmount());

        try {
            return UseBalance.Response.fromDto(transactionDto);
        } catch (AccountException e) {
            log.error("Failed to use balance.");

            transactionService.saveFailedUseTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );

            throw e;
        }
    }

    @PostMapping("/transaction/cancel")
    public CancelBalance.Response cancelBalance(
            @Valid @RequestBody CancelBalance.Request request
    ) {
        TransactionDto transactionDto
                = transactionService.cancelBalance(request.getTransactionId(), request.getAccountNumber(), request.getAmount());

        try {
            return CancelBalance.Response.fromDto(transactionDto);
        } catch (AccountException e) {
            log.error("Failed to use balance.");

            transactionService.saveFailedCancelTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );

            throw e;
        }
    }
}
