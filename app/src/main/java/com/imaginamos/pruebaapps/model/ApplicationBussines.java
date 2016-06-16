package com.imaginamos.pruebaapps.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by kmilopr on 14/02/2016.
 * Basically the CRUD for the entity Application
 */
public class ApplicationBussines {

    private Context context;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private SQLiteDatabase db;

    public ApplicationBussines(Context context){

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "applicationdb", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }


    public void createApplication(String name, String urlImageSmall, String urlImageMedium, String urlImageLarge, String summary, String currency, String type, String rights, String title, String link, String idLabel, String idNumber, String bundleId, String artist, String artistLink, String category, String categoryId, String scheme, Double price, String releaseDate){

        ApplicationDao applicationDao = daoSession.getApplicationDao();
        Application application = new Application();

        application.setName(name);
        application.setUrlImageSmall(urlImageSmall);
        application.setUrlImageMedium(urlImageMedium);
        application.setUrlImageLarge(urlImageLarge);
        application.setSummary(summary);
        application.setCurrency(currency);
        application.setType(type);
        application.setRights(rights);
        application.setTitle(title);
        application.setLink(link);
        application.setIdLabel(idLabel);
        application.setIdNumber(idNumber);
        application.setBundleId(bundleId);
        application.setArtist(artist);
        application.setArtistLink(artistLink);
        application.setCategory(category);
        application.setCategoryId(categoryId);
        application.setScheme(scheme);
        application.setPrice(price);
        application.setReleaseDate(releaseDate);
        applicationDao.insert(application);

    }

    public List<Application> getApplicationList(){
        QueryBuilder queryApplicationList = daoSession.getApplicationDao().queryBuilder();
        return queryApplicationList.list();
    }

    public Application getApplication(Long id){
        QueryBuilder<Application> queryApplicationList = daoSession.getApplicationDao().queryBuilder();
        Application app = queryApplicationList.where(ApplicationDao.Properties.Id.eq(id)).unique();
        return app;
    }

    public Long getApplicationListSize(){
        QueryBuilder queryApplicationList = daoSession.getApplicationDao().queryBuilder();
        return queryApplicationList.count();
    }

    public void deleteAll(){
        daoSession.getApplicationDao().deleteAll();
    }


    public void updateImagePhoto(String pathToPhoto, Long idApplication){
        ApplicationDao applicationDao = daoSession.getApplicationDao();
        Application app = getApplication(idApplication);
        app.setUrlImageLarge(pathToPhoto);
        applicationDao.update(app);
    }
}
