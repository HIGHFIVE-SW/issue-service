INSERT INTO issues (
    issue_id,
    title,
    content,
    issue_date,
    site_url,
    image_url,
    keyword
) VALUES
      (
          UNHEX(REPLACE(UUID(), '-', '')),
          '첫 번째 이슈 제목',
          '이것은 더미 콘텐츠입니다 – 첫 번째 이슈 예시입니다.',
          '2025-05-01 09:00:00',
          'https://example.com/issue/1',
          'https://example.com/images/issue1.png',
          'Environment'
      ),
      (
          UNHEX(REPLACE(UUID(), '-', '')),
          '두 번째 이슈 제목',
          '이것은 더미 콘텐츠입니다 – 두 번째 이슈 예시입니다.',
          '2025-05-02 10:30:00',
          'https://example.com/issue/2',
          'https://example.com/images/issue2.png',
          'Environment'
      ),
      (
          UNHEX(REPLACE(UUID(), '-', '')),
          '세 번째 이슈 제목',
          '이것은 더미 콘텐츠입니다 – 세 번째 이슈 예시입니다.',
          '2025-05-03 11:45:00',
          'https://example.com/issue/3',
          'https://example.com/images/issue3.png',
          'Society'
      ),
      (
          UNHEX(REPLACE(UUID(), '-', '')),
          '네 번째 이슈 제목',
          '이것은 더미 콘텐츠입니다 – 네 번째 이슈 예시입니다.',
          '2025-05-04 14:15:00',
          'https://example.com/issue/4',
          'https://example.com/images/issue4.png',
          'Society'
      ),
      (
          UNHEX(REPLACE(UUID(), '-', '')),
          '다섯 번째 이슈 제목',
          '이것은 더미 콘텐츠입니다 – 다섯 번째 이슈 예시입니다.',
          '2025-05-05 16:20:00',
          'https://example.com/issue/5',
          'https://example.com/images/issue5.png',
          'Environment'
      );
