// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: messaging.proto

package com.rntgroup.rpc.protocols.grpc.proto;

public interface RequestMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:messaging.RequestMessage)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.messaging.Type Type = 1;</code>
   */
  int getTypeValue();
  /**
   * <code>.messaging.Type Type = 1;</code>
   */
  Type getType();

  /**
   * <code>string message = 2;</code>
   */
  String getMessage();
  /**
   * <code>string message = 2;</code>
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>string sender = 3;</code>
   */
  String getSender();
  /**
   * <code>string sender = 3;</code>
   */
  com.google.protobuf.ByteString
      getSenderBytes();

  /**
   * <code>string receiver = 4;</code>
   */
  String getReceiver();
  /**
   * <code>string receiver = 4;</code>
   */
  com.google.protobuf.ByteString
      getReceiverBytes();
}
