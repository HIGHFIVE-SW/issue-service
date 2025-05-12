package com.trendist.issue_service.domain.issue.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.trendist.issue_service.global.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "issues")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "issue_id")
	private UUID id;

	@Column(name = "title")
	private String title;

	@Column(name = "content")
	private String content;

	@Column(name = "issue_date")
	private LocalDateTime issueDate;

	@Column(name = "site_url")
	private String siteUrl;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "keyword")
	private String keyword;
}
