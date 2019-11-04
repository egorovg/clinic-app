insert into doctor (name, surname, patronymic, specialization)
    values ('Игорь', 'Краевский', 'Михайлович', 'Терапевт');
insert into doctor (name, surname, patronymic, specialization)
    values ('Сергей', 'Янков', 'Филиппович', 'Анастезиолог');
insert into doctor (name, surname, patronymic, specialization)
    values ('Антон', 'Рощин', 'Андреевич', 'Пульмонолог');
insert into doctor (name, surname, patronymic, specialization)
    values ('Анна', 'Наумова', 'Витальевна', 'Эндокринолог');
insert into doctor (name, surname, patronymic, specialization)
    values ('Оксана', 'Дубова', 'Владимировна', 'Кардиолог');

insert into patient (name, surname, patronymic, phone)
    values ('Ольга', 'Харитонова', 'Вячеславовна', '9277053322');
insert into patient (name, surname, patronymic, phone)
    values ('Артем', 'Бирюков', 'Васильевич', '9032254178');
insert into patient (name, surname, patronymic, phone)
    values ('Олег', 'Никишин', 'Константинович', '9224079992');
insert into patient (name, surname, patronymic, phone)
    values ('Вадим', 'Баженов', 'Артурович', '9172075547');
insert into patient (name, surname, patronymic, phone)
    values ('Екатерина', 'Лисовская', 'Александровна', '9990055681');

insert into recipe (description, create_date, expiry_date, priority, doctor_id, patient_id)
    values ('Амиксин 3р. в день после еды.', '2019-10-25', '2019-11-10', 'Нормальный', 1, 3);
insert into recipe (description, create_date, expiry_date, priority, doctor_id, patient_id)
    values ('АЦЦ 1р. в день после еды.', '2019-10-28', '2019-11-15', 'Нормальный', 3, 1);
insert into recipe (description, create_date, expiry_date, priority, doctor_id, patient_id)
    values ('Кардикет 2р. в день перед едой.', '2019-10-25', '2019-11-10', 'Срочный', 5, 4);