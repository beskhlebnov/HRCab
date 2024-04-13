package com.example.hrcab.repos;

import com.example.hrcab.models.Feedback;
import com.example.hrcab.models.Vacancy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


//Репозиторий для работы с таблицей откликов в БД
@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long>{
        List<Feedback> findByUsersId(Long id);
        List<Feedback> findByVacancyId(Long id);
}