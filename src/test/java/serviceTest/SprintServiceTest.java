package serviceTest;


import service.SprintService;

import javax.inject.Inject;


public class SprintServiceTest {

    @Inject
    SprintService service;
//package serviceTest;
//
//import model.ChocoBox;
//import org.hibernate.query.Query;
//import org.junit.Assert;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.runners.MockitoJUnitRunner;
//import service.ChocoBoxService;
//
//import javax.persistence.EntityManager;
//import java.lang.reflect.Array;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Matchers.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//    @RunWith(MockitoJUnitRunner.class)
//    class ChocoBoxServiceTest {
//
//        @Mock
//        EntityManager entityManager = mock(EntityManager.class);
//
//
//        @InjectMocks
//        ChocoBoxService service = new ChocoBoxService(this.entityManager);
//
//        @BeforeEach
//        void setUp() throws Exception{
//            MockitoAnnotations.initMocks(this);
//        }
//
//        @Test
//        public void test(){
//
//            LocalDate date;
//            date = LocalDate.now();
//            ChocoBox[] chocoBoxes = new ChocoBox[1];
//            ChocoBox choco = new ChocoBox("alura", "Não sabe mockar", 1L, true,  date.plusDays(15));
//            chocoBoxes[0] = choco;
//            List<ChocoBox> chocoBoxList = new ArrayList<>();
//            chocoBoxList.add(choco);
//            Query query = mock(Query.class);
//            query.setParameter(0, choco);
//            when(entityManager.createNativeQuery("SELECT f FROM ChocoBox f ORDER BY f.name", ChocoBox.class)).thenReturn(query);
//            //when(query.getResultList()).thenReturn(Arrays.asList(chocoBoxes));
//            //when(service.findAll()).thenReturn(Arrays.asList(chocoBoxes));
//            System.out.println(query.getResultList() + "Teste");
//            Assert.assertEquals(service.findAll(), chocoBoxList);
//
//        }
//    }


}
