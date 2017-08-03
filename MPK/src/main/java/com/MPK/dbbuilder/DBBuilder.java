package com.MPK.dbbuilder;

import com.MPK.model.Line;
import com.MPK.model.Stop;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class DBBuilder {

    private List<Line> lineList;
    private SessionFactory factory;

    public DBBuilder() {
        lineList = new ArrayList<>();
        configureHibernate();
    }

    public void buildDB() {
        System.out.println("----RETRIEVING INFO FROM MPK SERVERS----");
        retrieveLines();
        retrieveStops();
        System.out.println("----DONE----");
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("----BUILDING DB-----");
            for (Line line : lineList) {
                session.save(line);
                for (Stop stop : line.getStops()) {
                    session.save(stop);
                }
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
        System.out.println("----DONE----");
    }

    private void retrieveLines() {
        LinesSupplier linesSupplier = new LinesSupplier();
        linesSupplier.supplyData(lineList);
    }

    private void retrieveStops() {
        StopsSupplier stopsSupplier = new StopsSupplier();
        stopsSupplier.supplyData(lineList);
    }

    private void configureHibernate() {
        Configuration config = new Configuration().configure("hibernate/hibernate.cfg.xml");
        config.setProperty("hbm2ddl.auto", "create");

        try {
            this.factory = config.buildSessionFactory();
        } catch (Throwable ex) {
            throw new HibernateException(ex.getMessage());
        }
    }
}
