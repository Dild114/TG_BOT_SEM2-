//package app.api.mapper;
//
//import app.api.dto.WebsiteDto;
//import app.api.entity.UserId;
//import app.api.entity.Website;
//import app.api.entity.User;
//import app.api.entity.WebsiteId;
//import app.api.repository.UserRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.stereotype.Component;
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class WebsiteMapper {
//  private final UserRepository userRepository;
//
//  public WebsiteDto toDto(Website website) {
//    return WebsiteDto.builder()
//        .id(website.getId())
//        .url(website.getUrl())
//        .userId(website.getUser().getId())
//        .build();
//  }
//
//  public Website toEntity(WebsiteDto dto) {
//    User user = userRepository.findById(new UserId(dto.getUserId()))
//        .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + dto.getUserId()));
//
//    return Website.builder()
//        .id(dto.getId())
//        .url(dto.getUrl())
//        .user(user)
//        .build();
//  }
//}
