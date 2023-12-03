package com.socialmedia.service;

import com.socialmedia.model.Content_Report;
import java.util.List;
import java.util.Optional;

public interface Content_ReportService {

    public List<Content_Report> getAll();

    public Optional<Content_Report> findById(int id);

    public Content_Report save(Content_Report content_report);

    public boolean delete(int id);
}
