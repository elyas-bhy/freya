/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2013-12-19 23:55:21 UTC)
 * on 2014-01-26 at 14:36:14 UTC 
 * Modify at your own risk.
 */

package com.appspot.freya_app.freya.model;

import java.util.ArrayList;

/**
 * Model definition for Artwork.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the freya. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Artwork extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Artist artist;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> comments;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String date;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Dimension dimension;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Photo> photos;

  static {
    // hack to force ProGuard to consider Photo used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Photo.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Reproduction> reproductions;

  static {
    // hack to force ProGuard to consider Reproduction used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(Reproduction.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String summary;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String support;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<java.lang.String> tags;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String technique;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String title;
  
  public Artwork() {
	  comments = new ArrayList<>();
	  tags = new ArrayList<>();
	  photos = new ArrayList<>();
	  reproductions = new ArrayList<>();
  }

  /**
   * @return value or {@code null} for none
   */
  public Artist getArtist() {
    return artist;
  }

  /**
   * @param artist artist or {@code null} for none
   */
  public Artwork setArtist(Artist artist) {
    this.artist = artist;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getComments() {
    return comments;
  }

  /**
   * @param comments comments or {@code null} for none
   */
  public Artwork setComments(java.util.List<java.lang.String> comments) {
    this.comments = comments;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDate() {
    return date;
  }

  /**
   * @param date date or {@code null} for none
   */
  public Artwork setDate(java.lang.String date) {
    this.date = date;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Dimension getDimension() {
    return dimension;
  }

  /**
   * @param dimension dimension or {@code null} for none
   */
  public Artwork setDimension(Dimension dimension) {
    this.dimension = dimension;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Artwork setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Photo> getPhotos() {
	if (photos == null)
		photos = new ArrayList<>();
    return photos;
  }

  /**
   * @param photos photos or {@code null} for none
   */
  public Artwork setPhotos(java.util.List<Photo> photos) {
    this.photos = photos;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Reproduction> getReproductions() {
	if (reproductions == null)
		reproductions = new ArrayList<>();
    return reproductions;
  }

  /**
   * @param reproductions reproductions or {@code null} for none
   */
  public Artwork setReproductions(java.util.List<Reproduction> reproductions) {
    this.reproductions = reproductions;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSummary() {
    return summary;
  }

  /**
   * @param summary summary or {@code null} for none
   */
  public Artwork setSummary(java.lang.String summary) {
    this.summary = summary;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getSupport() {
    return support;
  }

  /**
   * @param support support or {@code null} for none
   */
  public Artwork setSupport(java.lang.String support) {
    this.support = support;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<java.lang.String> getTags() {
    return tags;
  }

  /**
   * @param tags tags or {@code null} for none
   */
  public Artwork setTags(java.util.List<java.lang.String> tags) {
    this.tags = tags;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTechnique() {
    return technique;
  }

  /**
   * @param technique technique or {@code null} for none
   */
  public Artwork setTechnique(java.lang.String technique) {
    this.technique = technique;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTitle() {
    return title;
  }

  /**
   * @param title title or {@code null} for none
   */
  public Artwork setTitle(java.lang.String title) {
    this.title = title;
    return this;
  }

  @Override
  public Artwork set(String fieldName, Object value) {
    return (Artwork) super.set(fieldName, value);
  }

  @Override
  public Artwork clone() {
    return (Artwork) super.clone();
  }

}
