package com.example;

import com.example.ru.hogwarts.school.model.Student;
import org.yaml.snakeyaml.events.Event;

public class ConstantStudentTest {

    public static final long ID1 = 1;

    public static final long ID2 = 2;

    public static final String NAME1 = "Berychc";

    public static final String NAME2 = "Egor";

    public static final int AGE1 = 23;

    public static final int AGE2 = 22;

    public static final Student STUDENT1 = new Student(ID1, NAME1, AGE1);

    public static final Student STUDENT2 = new Student(ID2, NAME2, AGE2);
}
