// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

package com.service;

public final class Message {
  private Message() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_RecommendMovieRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_RecommendMovieRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_RecommendMovieResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_RecommendMovieResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_Movie_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_Movie_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_UserProfile_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_UserProfile_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_service_MovieProfile_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_service_MovieProfile_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rmessage.proto\022\007service\"?\n\025RecommendMov" +
      "ieRequest\022\017\n\007user_id\030\001 \001(\004\022\025\n\rrecommend_" +
      "way\030\002 \001(\004\"7\n\026RecommendMovieResponse\022\035\n\005m" +
      "ovie\030\001 \003(\0132\016.service.Movie\"(\n\005Movie\022\020\n\010m" +
      "ovie_id\030\001 \001(\004\022\r\n\005title\030\002 \001(\t\"B\n\013UserProf" +
      "ile\022\017\n\007user_id\030\001 \001(\004\022\021\n\tavg_score\030\002 \001(\002\022" +
      "\017\n\007userTag\030\003 \003(\004\"T\n\014MovieProfile\022\020\n\010movi" +
      "e_id\030\001 \001(\004\022\021\n\tavg_score\030\002 \001(\002\022\020\n\010movieTa" +
      "g\030\003 \003(\004\022\r\n\005title\030\004 \001(\t2b\n\007Service\022W\n\022get" +
      "RecommendMovies\022\036.service.RecommendMovie",
      "Request\032\037.service.RecommendMovieResponse" +
      "\"\000B\036\n\013com.serviceB\007MessageP\001\242\002\003HLWb\006prot" +
      "o3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_service_RecommendMovieRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_service_RecommendMovieRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_RecommendMovieRequest_descriptor,
        new java.lang.String[] { "UserId", "RecommendWay", });
    internal_static_service_RecommendMovieResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_service_RecommendMovieResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_RecommendMovieResponse_descriptor,
        new java.lang.String[] { "Movie", });
    internal_static_service_Movie_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_service_Movie_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_Movie_descriptor,
        new java.lang.String[] { "MovieId", "Title", });
    internal_static_service_UserProfile_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_service_UserProfile_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_UserProfile_descriptor,
        new java.lang.String[] { "UserId", "AvgScore", "UserTag", });
    internal_static_service_MovieProfile_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_service_MovieProfile_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_service_MovieProfile_descriptor,
        new java.lang.String[] { "MovieId", "AvgScore", "MovieTag", "Title", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
