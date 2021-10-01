package io.github.joxit.osm.service;

import io.github.joxit.osm.model.Tile;
import io.github.joxit.osm.utils.Svg;
import jdk.nashorn.internal.parser.JSONParser;
import mil.nga.sf.geojson.GeoJsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Service pour retourner les tuiles.
 *
 * @author Jones Magloire @Joxit
 * @since 2019-11-03
 */
@Service
public class TileService {
@Value("classpath:prefectures.geojson")
Resource res;
  /**
   * Ici il faut prendre les coordonnées de la tuile et renvoyer la donnée PNG associée.
   * Vous pouvez y ajouter des fonctionnalités en plus pour améliorer les perfs.
   *
   * @param tile qu'il faut renvoyer
   *
   * @return le byte array au format png
   */

  //On test sur 3 cas
  public byte[] getTile(Tile tile) {
    if(tile.getX() < 0 || tile.getY() < 0 || tile.getZ() < 0) {
      throw new IllegalArgumentException("Attention!! vos arguments sont négatifs");
    } else if(tile.getX() > Math.pow(2, tile.getZ()) || tile.getY() > Math.pow(2, tile.getZ())) {
      throw new IllegalArgumentException("Attention!! La valeur de X ou de Y est grande ");
    } else if(tile.getZ() > 24) {
      throw new IllegalArgumentException("Attention!! Z (ZOOM) ne doit pas dépasser 24");
    } else {
      return Svg.getTile(tile);
    }
  }


  /**
   * @return le contenu du fichier prefectures.geojson
   */



  public String getPrefectures() throws IOException {
    InputStream fichier = res.getInputStream();
    StringBuilder builder = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(fichier))) {
      String prefecture;

      while ((prefecture = reader.readLine()) != null)
        builder.append(prefecture);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return builder.toString();
  }




  /**
   * Il faudra créer votre DAO pour récuperer les données.
   * Utilisez ce que vous voulez pour faire le DAO.
   *
   * @return les éléments contenus dans la base de données
   */
  public GeoJsonObject getPOIs() {
    return null;
  }
}
