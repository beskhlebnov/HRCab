package com.example.hrcab.repos;

import com.example.hrcab.models.Users;
import com.example.hrcab.models.Vacancy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Репозиторий для работы с таблицей вакансий в БД
@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long>{

}