package pl.sdacademy.hibernate.hello.workshop1;

import pl.sdacademy.hibernate.hello.common.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Scanner;

//Program ma za zadanie znaleźć wiersz w tabeli Country po kodzie kraju (code)
// i zamienić nazwę kraju (name) na podaną przez użytkownika.

public class Workshop_UPDATE {
    public static void main(String[] args) {
        System.out.println("Podaj kod kraju:");
        final String code = new Scanner(System.in).nextLine();
        System.out.println("Podaj nazwę na jaką chcesz zmienić:");
        final String name = new Scanner(System.in).nextLine();

        final Country country = loadCountryByCode(code, name);
        System.out.println(country);
    }

    public static Country loadCountryByCode(String code, String name) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("HelloHibernatePU");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("UPDATE Country cou SET cou.name=:name WHERE cou.code=:code");
            query.setParameter("name",name);
            query.setParameter("code",code);
            query.executeUpdate();
            entityManager.getTransaction().commit();

            return entityManager.find(Country.class,code);

        } finally {
            entityManagerFactory.close();
        }

    }
}
