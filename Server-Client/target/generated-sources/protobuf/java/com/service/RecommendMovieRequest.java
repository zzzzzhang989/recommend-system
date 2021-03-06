// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

package com.service;

/**
 * Protobuf type {@code service.RecommendMovieRequest}
 */
public  final class RecommendMovieRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:service.RecommendMovieRequest)
    RecommendMovieRequestOrBuilder {
  // Use RecommendMovieRequest.newBuilder() to construct.
  private RecommendMovieRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private RecommendMovieRequest() {
    userId_ = 0L;
    recommendWay_ = 0L;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private RecommendMovieRequest(
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

            userId_ = input.readUInt64();
            break;
          }
          case 16: {

            recommendWay_ = input.readUInt64();
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
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.service.Message.internal_static_service_RecommendMovieRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.service.Message.internal_static_service_RecommendMovieRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.service.RecommendMovieRequest.class, com.service.RecommendMovieRequest.Builder.class);
  }

  public static final int USER_ID_FIELD_NUMBER = 1;
  private long userId_;
  /**
   * <code>optional uint64 user_id = 1;</code>
   */
  public long getUserId() {
    return userId_;
  }

  public static final int RECOMMEND_WAY_FIELD_NUMBER = 2;
  private long recommendWay_;
  /**
   * <code>optional uint64 recommend_way = 2;</code>
   */
  public long getRecommendWay() {
    return recommendWay_;
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
    if (userId_ != 0L) {
      output.writeUInt64(1, userId_);
    }
    if (recommendWay_ != 0L) {
      output.writeUInt64(2, recommendWay_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (userId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(1, userId_);
    }
    if (recommendWay_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(2, recommendWay_);
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
    if (!(obj instanceof com.service.RecommendMovieRequest)) {
      return super.equals(obj);
    }
    com.service.RecommendMovieRequest other = (com.service.RecommendMovieRequest) obj;

    boolean result = true;
    result = result && (getUserId()
        == other.getUserId());
    result = result && (getRecommendWay()
        == other.getRecommendWay());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + USER_ID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getUserId());
    hash = (37 * hash) + RECOMMEND_WAY_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getRecommendWay());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.service.RecommendMovieRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.service.RecommendMovieRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.service.RecommendMovieRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.service.RecommendMovieRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.service.RecommendMovieRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.service.RecommendMovieRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.service.RecommendMovieRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.service.RecommendMovieRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.service.RecommendMovieRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.service.RecommendMovieRequest parseFrom(
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
  public static Builder newBuilder(com.service.RecommendMovieRequest prototype) {
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
   * Protobuf type {@code service.RecommendMovieRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:service.RecommendMovieRequest)
      com.service.RecommendMovieRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.service.Message.internal_static_service_RecommendMovieRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.service.Message.internal_static_service_RecommendMovieRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.service.RecommendMovieRequest.class, com.service.RecommendMovieRequest.Builder.class);
    }

    // Construct using com.service.RecommendMovieRequest.newBuilder()
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
      userId_ = 0L;

      recommendWay_ = 0L;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.service.Message.internal_static_service_RecommendMovieRequest_descriptor;
    }

    public com.service.RecommendMovieRequest getDefaultInstanceForType() {
      return com.service.RecommendMovieRequest.getDefaultInstance();
    }

    public com.service.RecommendMovieRequest build() {
      com.service.RecommendMovieRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.service.RecommendMovieRequest buildPartial() {
      com.service.RecommendMovieRequest result = new com.service.RecommendMovieRequest(this);
      result.userId_ = userId_;
      result.recommendWay_ = recommendWay_;
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
      if (other instanceof com.service.RecommendMovieRequest) {
        return mergeFrom((com.service.RecommendMovieRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.service.RecommendMovieRequest other) {
      if (other == com.service.RecommendMovieRequest.getDefaultInstance()) return this;
      if (other.getUserId() != 0L) {
        setUserId(other.getUserId());
      }
      if (other.getRecommendWay() != 0L) {
        setRecommendWay(other.getRecommendWay());
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
      com.service.RecommendMovieRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.service.RecommendMovieRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long userId_ ;
    /**
     * <code>optional uint64 user_id = 1;</code>
     */
    public long getUserId() {
      return userId_;
    }
    /**
     * <code>optional uint64 user_id = 1;</code>
     */
    public Builder setUserId(long value) {
      
      userId_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional uint64 user_id = 1;</code>
     */
    public Builder clearUserId() {
      
      userId_ = 0L;
      onChanged();
      return this;
    }

    private long recommendWay_ ;
    /**
     * <code>optional uint64 recommend_way = 2;</code>
     */
    public long getRecommendWay() {
      return recommendWay_;
    }
    /**
     * <code>optional uint64 recommend_way = 2;</code>
     */
    public Builder setRecommendWay(long value) {
      
      recommendWay_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>optional uint64 recommend_way = 2;</code>
     */
    public Builder clearRecommendWay() {
      
      recommendWay_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:service.RecommendMovieRequest)
  }

  // @@protoc_insertion_point(class_scope:service.RecommendMovieRequest)
  private static final com.service.RecommendMovieRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.service.RecommendMovieRequest();
  }

  public static com.service.RecommendMovieRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RecommendMovieRequest>
      PARSER = new com.google.protobuf.AbstractParser<RecommendMovieRequest>() {
    public RecommendMovieRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new RecommendMovieRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<RecommendMovieRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RecommendMovieRequest> getParserForType() {
    return PARSER;
  }

  public com.service.RecommendMovieRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

