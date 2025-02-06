package online.abhijeetadarsh.hms.controller;

import lombok.RequiredArgsConstructor;
import online.abhijeetadarsh.hms.dto.DoctorResponse;
import online.abhijeetadarsh.hms.service.DoctorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {
    private final DoctorService doctorService;

    @GetMapping("/doctors")
    public ResponseEntity<Page<DoctorResponse>> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(doctorService.getAllDoctors(pageRequest));
    }
}
