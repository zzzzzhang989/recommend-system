// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

package com.service;

/**
 * Protobuf type {@code service.MovieProfile}
 */
public  final class MovieProfile extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:service.MovieProfile)
    MovieProfileOrBuilder {
  // Use MovieProfile.newBuilder() to construct.
  private MovieProfile(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MovieProfile() {
    movieId_ = 0L;
    avgScore_ = 0F;
    movieTag_ = java.util.Collections.emptyList();
    title_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private MovieProfile(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            movieId_ = input.readUInt64();
            break;
          }
          case 21: {

            avgScore_ = input.readFloat();
            break;
          }
          case 24: {
            if (!((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
              movieTag_ = new java.util.ArrayList<java.lang.Long>();
              mutable_bitField0_ |= 0x00000004;
            }
            movieTag_.add(input.readUInt64());
            break;
          }
          case 26: {
            int length = input.readRawVarint32();
            int limit = input.pushLimit(length);
            if (!((mutable_bitField0_ & 0x00000004) == 0x00000004) && input.getBytesUntilLimit() > 0) {
              movieTag_ = new java.util.ArrayList<java.lang.Long>();
              mutable_bitField0_ |= 0x00000004;
            }
            while (input.getBytesUntilLimit() > 0) {
              movieTag_.add(input.readUInt64());
            }
            input.popLimit(limit);
            break;
          }
          case 34: {
            java.lang.String s = input.readStringRequireUtf8();

            title_ = s;
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
        movieTag_ = java.util.Collections.unmodifiableList(movieTag_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.service.Message.internal_static_service_MovieProfile_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.service.Message.internal_static_service_MovieProfile_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.service.MovieProfile.class, com.service.MovieProfile.Builder.class);
  }

  private int bitField0_;
  public static final int MOVIE_ID_FIELD_NUMBER = 1;
  private long movieId_;
  /**
   * <code>optional uint64 movie_id = 1;</code>
   */
  public long getMovieId() {
    return movieId_;
  }

  public static final int AVG_SCORE_FIELD_NUMBER = 2;
  private float avgScore_;
  /**
   * <code>optional float avg_score = 2;</code>
   */
  public float getAvgScore() {
    return avgScore_;
  }

  public static final int MOVIETAG_FIELD_NUMBER = 3;
  private java.util.List<java.lang.Long> movieTag_;
  /**
   * <code>repeated uint64 movieTag = 3;</code>
   */
  public java.util.List<java.lang.Long>
      getMovieTagList() {
    return movieTag_;
  }
  /**
   * <code>repeated uint64 movieTag = 3;</code>
   */
  public int getMovieTagCount() {
    return movieTag_.size();
  }
  /**
   * <code>repeated uint64 movieTag = 3;</code>
   */
  public long getMovieTag(int index) {
    return movieTag_.get(index);
  }
  private int movieTagMemoizedSerializedSize = -1;

  public static final int TITLE_FIELD_NUMBER = 4;
  private volatile java.lang.Object title_;
  /**
   * <code>optional string title = 4;</code>
   */
  public java.lang.String getTitle() {
    java.lang.Object ref = title_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      title_ = s;
      return s;
    }
  }
  /**
   * <code>optional string title = 4;</code>
   */
  public com.google.protobuf.ByteString
      getTitleBytes() {
    java.lang.Object ref = title_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      title_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    getSerializedSize();
    if (movieId_ != 0L) {
      output.writeUInt64(1, movieId_);
    }
    if (avgScore_ != 0F) {
      output.writeFloat(2, avgScore_);
    }
    if (getMovieTagList().size() > 0) {
      output.writeUInt32NoTag(26);
      output.writeUInt32NoTag(movieTagMemoizedSerializedSize);
    }
    for (int i = 0; i < movieTag_.size(); i++) {
      output.writeUInt64NoTag(movieTag_.get(i));
    }
    if (!getTitleBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, title_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (movieId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(1, movieId_);
    }
    if (avgScore_ != 0F) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(2, avgScore_);
    }
    {
      int dataSize = 0;
      for (int i = 0; i < movieTag_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeUInt64SizeNoTag(movieTag_.get(i));
      }
      size += dataSize;
      if (!getMovieTagList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dataSize);
      }
      movieTagMemoizedSerializedSize = dataSize;
    }
    if (!getTitleBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, title_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.service.MovieProfile)) {
      return super.equals(obj);
    }
    com.service.MovieProfile other = (com.service.MovieProfile) obj;

    boolean result = true;
    result = result && (getMovieId()
        == other.getMovieId());
    result = result && (
        java.lang.Float.floatToIntBits(getAvgScore())
        == java.lang.Float.floatToIntBits(
            other.getAvgScore()));
    result = result && getMovieTagList()
        .equals(other.getMovieTagList());
    result = result && getTitle()
        .equals(other.getTitle());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + MOVIE_ID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getMovieId());
    hash = (37 * hash) + AVG_SCORE_FIELD_NUMBER;
    hash = (53 * hash) + java.lang.Float.floatToIntBits(
        getAvgScore());
    if (getMovieTagCount() > 0) {
      hash = (37 * hash) + MOVIETAG_FIELD_NUMBER;
      hash = (53 * hash) + getMovieTagList().hashCode();
    }
    hash = (37 * hash) + TITLE_FIELD_NUMBER;
    hash = (53 * hash) + getTitle().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.service.MovieProfile parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.service.MovieProfile parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.service.MovieProfile parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.service.MovieProfile parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.service.MovieProfile parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.service.MovieProfile parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.service.MovieProfile parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.service.MovieProfile parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.service.MovieProfile parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.service.MovieProfile parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.service.MovieProfile prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code service.MovieProfile}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:service.MovieProfile)
      com.service.MovieProfileOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.service.Message.internal_static_service_MovieProfile_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.service.Message.internal_static_service_MovieProfile_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.service.MovieProfile.class, com.service.MovieProfile.Builder.class);
    }

    // Construct using com.service.MovieProfile.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      movieId_ = 0L;

      avgScore_ = 0F;

      movieTag_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000004);
      title_ = "";

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.service.Message.internal_static_service_MovieProfile_descriptor;
    }

    public com.service.MovieProfile getDefaultInstanceForType() {
      return com.service.MovieProfile.getDefaultInstance();
    }

    public com.service.MovieProfile build() {
      com.service.MovieProfile result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.service.MovieProfile buildPartial() {
      com.service.MovieProfile result = new com.service.MovieProfile(this);
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      result.movieId_ = movieId_;
      result.avgScore_ = avgScore_;
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        movieTag_ = java.util.Collections.unmodifiableList(movieTag_);
        bitField0_ = (bitField0_ & ~0x00000004);
      }
      result.movieTag_ = movieTag_;
      result.title_ = title_;
      result.bitField0_ = to_bitField0_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.service.MovieProfile) {
        return mergeFrom((com.service.MovieProfile)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.service.MovieProfile other) {
      if (other == com.service.MovieProfile.getDefaultInstance()) return this;
      if (other.getMovieId() != 0L) {
        setMovieId(other.getMovieId());
      }
      if (other.getAvgScore() != 0F) {
        setAvgScore(other.getAvgScore());
      }
      if (!other.movieTag_.isEmpty()) {
        if (movieTag_.isEmpty()) {
          movieTag_ = other.movieTag_;
          bitField0_ = (bitField0_ & ~0x00000004);
        } else {
          ensureMovieTagIsMutable();
          movieTag_.addAll(other.movieTag_);
        }
        onChanged();
      }
      if (!other.getTitle().isEmpty()) {
        title_ = other.title_;
        onChanged();
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.service.MovieProfile parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.service.MovieProfile) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private long movieId_ ;
    /**
     * <code>optional uint64 movie_id = 1;</code>
     */
    public long getMovieId() {
      return movieId_;
    }
    /**
     * <code>optional uint64 movie_id = 1;</code>
     */
    public Builder setMovieId(long value) {
      
      movieId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional uint64 movie_id = 1;</code>
     */
    public Builder clearMovieId() {
      
      movieId_ = 0L;
      onChanged();
      return this;
    }

    private float avgScore_ ;
    /**
     * <code>optional float avg_score = 2;</code>
     */
    public float getAvgScore() {
      return avgScore_;
    }
    /**
     * <code>optional float avg_score = 2;</code>
     */
    public Builder setAvgScore(float value) {
      
      avgScore_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional float avg_score = 2;</code>
     */
    public Builder clearAvgScore() {
      
      avgScore_ = 0F;
      onChanged();
      return this;
    }

    private java.util.List<java.lang.Long> movieTag_ = java.util.Collections.emptyList();
    private void ensureMovieTagIsMutable() {
      if (!((bitField0_ & 0x00000004) == 0x00000004)) {
        movieTag_ = new java.util.ArrayList<java.lang.Long>(movieTag_);
        bitField0_ |= 0x00000004;
       }
    }
    /**
     * <code>repeated uint64 movieTag = 3;</code>
     */
    public java.util.List<java.lang.Long>
        getMovieTagList() {
      return java.util.Collections.unmodifiableList(movieTag_);
    }
    /**
     * <code>repeated uint64 movieTag = 3;</code>
     */
    public int getMovieTagCount() {
      return movieTag_.size();
    }
    /**
     * <code>repeated uint64 movieTag = 3;</code>
     */
    public long getMovieTag(int index) {
      return movieTag_.get(index);
    }
    /**
     * <code>repeated uint64 movieTag = 3;</code>
     */
    public Builder setMovieTag(
        int index, long value) {
      ensureMovieTagIsMutable();
      movieTag_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated uint64 movieTag = 3;</code>
     */
    public Builder addMovieTag(long value) {
      ensureMovieTagIsMutable();
      movieTag_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated uint64 movieTag = 3;</code>
     */
    public Builder addAllMovieTag(
        java.lang.Iterable<? extends java.lang.Long> values) {
      ensureMovieTagIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, movieTag_);
      onChanged();
      return this;
    }
    /**
     * <code>repeated uint64 movieTag = 3;</code>
     */
    public Builder clearMovieTag() {
      movieTag_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }

    private java.lang.Object title_ = "";
    /**
     * <code>optional string title = 4;</code>
     */
    public java.lang.String getTitle() {
      java.lang.Object ref = title_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        title_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string title = 4;</code>
     */
    public com.google.protobuf.ByteString
        getTitleBytes() {
      java.lang.Object ref = title_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        title_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string title = 4;</code>
     */
    public Builder setTitle(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      title_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional string title = 4;</code>
     */
    public Builder clearTitle() {
      
      title_ = getDefaultInstance().getTitle();
      onChanged();
      return this;
    }
    /**
     * <code>optional string title = 4;</code>
     */
    public Builder setTitleBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      title_ = value;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:service.MovieProfile)
  }

  // @@protoc_insertion_point(class_scope:service.MovieProfile)
  private static final com.service.MovieProfile DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.service.MovieProfile();
  }

  public static com.service.MovieProfile getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MovieProfile>
      PARSER = new com.google.protobuf.AbstractParser<MovieProfile>() {
    public MovieProfile parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new MovieProfile(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MovieProfile> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<MovieProfile> getParserForType() {
    return PARSER;
  }

  public com.service.MovieProfile getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
