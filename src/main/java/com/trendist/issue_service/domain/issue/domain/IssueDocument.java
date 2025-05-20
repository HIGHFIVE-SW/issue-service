package com.trendist.issue_service.domain.issue.domain;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Document(indexName = "mysql-trendist.trendist.issues")
@Builder
@Getter
public class IssueDocument {
	@Id
	private String id;

	@MultiField(
		mainField = @Field(name = "title", type = FieldType.Text, analyzer = "standard"),
		otherFields = {
			@InnerField(suffix = "keyword", type = FieldType.Keyword)
		}
	)
	private String title;

	@Field(name = "keyword", type = FieldType.Keyword)
	private String keyword;

	@Field(name = "issue_date", type = FieldType.Keyword)
	private String issueDate;

	@Field(name = "image_url", type = FieldType.Keyword)
	private String imageUrl;
}
