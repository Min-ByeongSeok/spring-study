package zerobase.mission.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.mission.domain.member.Manager;
import zerobase.mission.dto.SignupManagerDto;
import zerobase.mission.dto.member.ManagerDto;
import zerobase.mission.service.ManagerService;
import zerobase.mission.service.RegisterService;
import zerobase.mission.type.Role;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final RegisterService registerService;

    @PostMapping("/signup/manager")
    public SignupManagerDto.Response SignupManger(@RequestBody @Valid SignupManagerDto.Request request) {

        ManagerDto managerDto = managerService.signupManager(Manager.builder()
                .name(request.getName())
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .role(Role.MANAGER)
                .store(registerService.findRegisteredStore(request.getStoreName(), request.getAddress()))
                .build());

        return SignupManagerDto.Response.fromDto(managerDto);
    }

}
