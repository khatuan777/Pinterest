package com.socialmedia.service;

import com.socialmedia.model.Report_Pins;
import com.socialmedia.model.Users;
import java.util.List;

public interface Report_PinService {
    
    public Report_Pins saveReportPins(Report_Pins reportPin);

    public List<Report_Pins> getAllReportPin();

    public long countAll();

    public long countByUserRatify(Users userRatify);

    public long countByApprove(boolean approve);

    public void changeApprove(int id, Users userRatify, boolean currentState);

}
