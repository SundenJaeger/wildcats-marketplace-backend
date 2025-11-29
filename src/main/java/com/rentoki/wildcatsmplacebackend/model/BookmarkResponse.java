package com.rentoki.wildcatsmplacebackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookmarkResponse {
    private Integer bookmarkId;
    private Integer studentId;
    private Integer resourceId;
    private String resourceTitle;
    private String resourceDescription;
    private String resourcePrice;
    private String resourceStatus;
    private LocalDateTime dateSaved;
}