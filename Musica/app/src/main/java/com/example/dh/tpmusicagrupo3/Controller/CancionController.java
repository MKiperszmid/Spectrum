package com.example.dh.tpmusicagrupo3.Controller;

import com.example.dh.tpmusicagrupo3.Model.DAO.CancionDao;
import com.example.dh.tpmusicagrupo3.Model.POJO.Cancion;

import java.util.List;

/**
 * Created by DH on 8/6/2018.
 */

public class CancionController {

    CancionDao cancionDao = new CancionDao();

    public List<Cancion> GetCanciones(){ return cancionDao.GetCanciones(); }
}
