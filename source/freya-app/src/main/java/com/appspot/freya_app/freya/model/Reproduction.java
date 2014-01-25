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
 * on 2014-01-25 at 18:16:03 UTC 
 * Modify at your own risk.
 */

package com.appspot.freya_app.freya.model;

/**
 * Model definition for Reproduction.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the freya. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Reproduction extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String artworkId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double price;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer stock;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String support;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getArtworkId() {
    return artworkId;
  }

  /**
   * @param artworkId artworkId or {@code null} for none
   */
  public Reproduction setArtworkId(java.lang.String artworkId) {
    this.artworkId = artworkId;
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
  public Reproduction setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getPrice() {
    return price;
  }

  /**
   * @param price price or {@code null} for none
   */
  public Reproduction setPrice(java.lang.Double price) {
    this.price = price;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getStock() {
    return stock;
  }

  /**
   * @param stock stock or {@code null} for none
   */
  public Reproduction setStock(java.lang.Integer stock) {
    this.stock = stock;
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
  public Reproduction setSupport(java.lang.String support) {
    this.support = support;
    return this;
  }

  @Override
  public Reproduction set(String fieldName, Object value) {
    return (Reproduction) super.set(fieldName, value);
  }

  @Override
  public Reproduction clone() {
    return (Reproduction) super.clone();
  }

}
