package com.socialmedia.service;


import com.socialmedia.model.Report_Comments;
import com.socialmedia.model.Users;

public interface Report_CommentService {
    
    public Report_Comments saveReportComments(Report_Comments reportComments);

    public long countAll();

    public long countByUserRatify(Users userRatify);

    public long countByApprove(boolean approve);
    
    public void changeApprove(int id, Users userRatify, boolean currentState);

}