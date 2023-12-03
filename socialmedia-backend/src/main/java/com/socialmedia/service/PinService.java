package com.socialmedia.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.socialmedia.model.Pins;
import com.socialmedia.model.Types;
import com.socialmedia.model.Users;

public interface PinService {

    public List<Pins> findByUserOrderByIdAsc(Users user);

    public List<Pins> getAllPins();

    public Optional<Pins> getPinById(int id);

    public List<Pins> getPinsByType(Types type);

    public List<Pins> getPinsByUser(Optional<Users> user);

    public Pins save(Pins pin);

    public boolean delete(int id);

    public long countAll();

    public long countByCreatedAtBefore(Date date);

    public long countByCreatedAt(Date date);

    public long countByCreatedAt(Date date1, Date date2);


}
