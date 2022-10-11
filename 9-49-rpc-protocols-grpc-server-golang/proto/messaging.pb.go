// Code generated by protoc-gen-go. DO NOT EDIT.
// versions:
// 	protoc-gen-go v1.28.1
// 	protoc        v3.21.6
// source: messaging.proto

package proto

import (
	protoreflect "google.golang.org/protobuf/reflect/protoreflect"
	protoimpl "google.golang.org/protobuf/runtime/protoimpl"
	reflect "reflect"
	sync "sync"
)

const (
	// Verify that this generated code is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(20 - protoimpl.MinVersion)
	// Verify that runtime/protoimpl is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(protoimpl.MaxVersion - 20)
)

type Type int32

const (
	Type_SMS        Type = 0
	Type_LETTER     Type = 1
	Type_PIGEON     Type = 2
	Type_ICQ        Type = 3
	Type_TELEPATHIC Type = 4
	Type_WILL       Type = 5
)

// Enum value maps for Type.
var (
	Type_name = map[int32]string{
		0: "SMS",
		1: "LETTER",
		2: "PIGEON",
		3: "ICQ",
		4: "TELEPATHIC",
		5: "WILL",
	}
	Type_value = map[string]int32{
		"SMS":        0,
		"LETTER":     1,
		"PIGEON":     2,
		"ICQ":        3,
		"TELEPATHIC": 4,
		"WILL":       5,
	}
)

func (x Type) Enum() *Type {
	p := new(Type)
	*p = x
	return p
}

func (x Type) String() string {
	return protoimpl.X.EnumStringOf(x.Descriptor(), protoreflect.EnumNumber(x))
}

func (Type) Descriptor() protoreflect.EnumDescriptor {
	return file_messaging_proto_enumTypes[0].Descriptor()
}

func (Type) Type() protoreflect.EnumType {
	return &file_messaging_proto_enumTypes[0]
}

func (x Type) Number() protoreflect.EnumNumber {
	return protoreflect.EnumNumber(x)
}

// Deprecated: Use Type.Descriptor instead.
func (Type) EnumDescriptor() ([]byte, []int) {
	return file_messaging_proto_rawDescGZIP(), []int{0}
}

type RequestMessage struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Type     Type   `protobuf:"varint,1,opt,name=Type,proto3,enum=Type" json:"Type,omitempty"`
	Message  string `protobuf:"bytes,2,opt,name=message,proto3" json:"message,omitempty"`
	Sender   string `protobuf:"bytes,3,opt,name=sender,proto3" json:"sender,omitempty"`
	Receiver string `protobuf:"bytes,4,opt,name=receiver,proto3" json:"receiver,omitempty"`
}

func (x *RequestMessage) Reset() {
	*x = RequestMessage{}
	if protoimpl.UnsafeEnabled {
		mi := &file_messaging_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *RequestMessage) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*RequestMessage) ProtoMessage() {}

func (x *RequestMessage) ProtoReflect() protoreflect.Message {
	mi := &file_messaging_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use RequestMessage.ProtoReflect.Descriptor instead.
func (*RequestMessage) Descriptor() ([]byte, []int) {
	return file_messaging_proto_rawDescGZIP(), []int{0}
}

func (x *RequestMessage) GetType() Type {
	if x != nil {
		return x.Type
	}
	return Type_SMS
}

func (x *RequestMessage) GetMessage() string {
	if x != nil {
		return x.Message
	}
	return ""
}

func (x *RequestMessage) GetSender() string {
	if x != nil {
		return x.Sender
	}
	return ""
}

func (x *RequestMessage) GetReceiver() string {
	if x != nil {
		return x.Receiver
	}
	return ""
}

type ResponseMessage struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Type     Type   `protobuf:"varint,1,opt,name=Type,proto3,enum=Type" json:"Type,omitempty"`
	Message  string `protobuf:"bytes,2,opt,name=message,proto3" json:"message,omitempty"`
	Sender   string `protobuf:"bytes,3,opt,name=sender,proto3" json:"sender,omitempty"`
	Receiver string `protobuf:"bytes,4,opt,name=receiver,proto3" json:"receiver,omitempty"`
}

func (x *ResponseMessage) Reset() {
	*x = ResponseMessage{}
	if protoimpl.UnsafeEnabled {
		mi := &file_messaging_proto_msgTypes[1]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *ResponseMessage) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*ResponseMessage) ProtoMessage() {}

func (x *ResponseMessage) ProtoReflect() protoreflect.Message {
	mi := &file_messaging_proto_msgTypes[1]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use ResponseMessage.ProtoReflect.Descriptor instead.
func (*ResponseMessage) Descriptor() ([]byte, []int) {
	return file_messaging_proto_rawDescGZIP(), []int{1}
}

func (x *ResponseMessage) GetType() Type {
	if x != nil {
		return x.Type
	}
	return Type_SMS
}

func (x *ResponseMessage) GetMessage() string {
	if x != nil {
		return x.Message
	}
	return ""
}

func (x *ResponseMessage) GetSender() string {
	if x != nil {
		return x.Sender
	}
	return ""
}

func (x *ResponseMessage) GetReceiver() string {
	if x != nil {
		return x.Receiver
	}
	return ""
}

var File_messaging_proto protoreflect.FileDescriptor

var file_messaging_proto_rawDesc = []byte{
	0x0a, 0x0f, 0x6d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x69, 0x6e, 0x67, 0x2e, 0x70, 0x72, 0x6f, 0x74,
	0x6f, 0x22, 0x79, 0x0a, 0x0e, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x4d, 0x65, 0x73, 0x73,
	0x61, 0x67, 0x65, 0x12, 0x19, 0x0a, 0x04, 0x54, 0x79, 0x70, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28,
	0x0e, 0x32, 0x05, 0x2e, 0x54, 0x79, 0x70, 0x65, 0x52, 0x04, 0x54, 0x79, 0x70, 0x65, 0x12, 0x18,
	0x0a, 0x07, 0x6d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52,
	0x07, 0x6d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0x12, 0x16, 0x0a, 0x06, 0x73, 0x65, 0x6e, 0x64,
	0x65, 0x72, 0x18, 0x03, 0x20, 0x01, 0x28, 0x09, 0x52, 0x06, 0x73, 0x65, 0x6e, 0x64, 0x65, 0x72,
	0x12, 0x1a, 0x0a, 0x08, 0x72, 0x65, 0x63, 0x65, 0x69, 0x76, 0x65, 0x72, 0x18, 0x04, 0x20, 0x01,
	0x28, 0x09, 0x52, 0x08, 0x72, 0x65, 0x63, 0x65, 0x69, 0x76, 0x65, 0x72, 0x22, 0x7a, 0x0a, 0x0f,
	0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x4d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0x12,
	0x19, 0x0a, 0x04, 0x54, 0x79, 0x70, 0x65, 0x18, 0x01, 0x20, 0x01, 0x28, 0x0e, 0x32, 0x05, 0x2e,
	0x54, 0x79, 0x70, 0x65, 0x52, 0x04, 0x54, 0x79, 0x70, 0x65, 0x12, 0x18, 0x0a, 0x07, 0x6d, 0x65,
	0x73, 0x73, 0x61, 0x67, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52, 0x07, 0x6d, 0x65, 0x73,
	0x73, 0x61, 0x67, 0x65, 0x12, 0x16, 0x0a, 0x06, 0x73, 0x65, 0x6e, 0x64, 0x65, 0x72, 0x18, 0x03,
	0x20, 0x01, 0x28, 0x09, 0x52, 0x06, 0x73, 0x65, 0x6e, 0x64, 0x65, 0x72, 0x12, 0x1a, 0x0a, 0x08,
	0x72, 0x65, 0x63, 0x65, 0x69, 0x76, 0x65, 0x72, 0x18, 0x04, 0x20, 0x01, 0x28, 0x09, 0x52, 0x08,
	0x72, 0x65, 0x63, 0x65, 0x69, 0x76, 0x65, 0x72, 0x2a, 0x4a, 0x0a, 0x04, 0x54, 0x79, 0x70, 0x65,
	0x12, 0x07, 0x0a, 0x03, 0x53, 0x4d, 0x53, 0x10, 0x00, 0x12, 0x0a, 0x0a, 0x06, 0x4c, 0x45, 0x54,
	0x54, 0x45, 0x52, 0x10, 0x01, 0x12, 0x0a, 0x0a, 0x06, 0x50, 0x49, 0x47, 0x45, 0x4f, 0x4e, 0x10,
	0x02, 0x12, 0x07, 0x0a, 0x03, 0x49, 0x43, 0x51, 0x10, 0x03, 0x12, 0x0e, 0x0a, 0x0a, 0x54, 0x45,
	0x4c, 0x45, 0x50, 0x41, 0x54, 0x48, 0x49, 0x43, 0x10, 0x04, 0x12, 0x08, 0x0a, 0x04, 0x57, 0x49,
	0x4c, 0x4c, 0x10, 0x05, 0x32, 0x44, 0x0a, 0x10, 0x4d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x69, 0x6e,
	0x67, 0x53, 0x65, 0x72, 0x76, 0x69, 0x63, 0x65, 0x12, 0x30, 0x0a, 0x0b, 0x73, 0x65, 0x6e, 0x64,
	0x4d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0x12, 0x0f, 0x2e, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73,
	0x74, 0x4d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0x1a, 0x10, 0x2e, 0x52, 0x65, 0x73, 0x70, 0x6f,
	0x6e, 0x73, 0x65, 0x4d, 0x65, 0x73, 0x73, 0x61, 0x67, 0x65, 0x42, 0x0a, 0x50, 0x01, 0x5a, 0x06,
	0x70, 0x72, 0x6f, 0x74, 0x6f, 0x2f, 0x62, 0x06, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x33,
}

var (
	file_messaging_proto_rawDescOnce sync.Once
	file_messaging_proto_rawDescData = file_messaging_proto_rawDesc
)

func file_messaging_proto_rawDescGZIP() []byte {
	file_messaging_proto_rawDescOnce.Do(func() {
		file_messaging_proto_rawDescData = protoimpl.X.CompressGZIP(file_messaging_proto_rawDescData)
	})
	return file_messaging_proto_rawDescData
}

var file_messaging_proto_enumTypes = make([]protoimpl.EnumInfo, 1)
var file_messaging_proto_msgTypes = make([]protoimpl.MessageInfo, 2)
var file_messaging_proto_goTypes = []interface{}{
	(Type)(0),               // 0: Type
	(*RequestMessage)(nil),  // 1: RequestMessage
	(*ResponseMessage)(nil), // 2: ResponseMessage
}
var file_messaging_proto_depIdxs = []int32{
	0, // 0: RequestMessage.Type:type_name -> Type
	0, // 1: ResponseMessage.Type:type_name -> Type
	1, // 2: MessagingService.sendMessage:input_type -> RequestMessage
	2, // 3: MessagingService.sendMessage:output_type -> ResponseMessage
	3, // [3:4] is the sub-list for method output_type
	2, // [2:3] is the sub-list for method input_type
	2, // [2:2] is the sub-list for extension type_name
	2, // [2:2] is the sub-list for extension extendee
	0, // [0:2] is the sub-list for field type_name
}

func init() { file_messaging_proto_init() }
func file_messaging_proto_init() {
	if File_messaging_proto != nil {
		return
	}
	if !protoimpl.UnsafeEnabled {
		file_messaging_proto_msgTypes[0].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*RequestMessage); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_messaging_proto_msgTypes[1].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*ResponseMessage); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
	}
	type x struct{}
	out := protoimpl.TypeBuilder{
		File: protoimpl.DescBuilder{
			GoPackagePath: reflect.TypeOf(x{}).PkgPath(),
			RawDescriptor: file_messaging_proto_rawDesc,
			NumEnums:      1,
			NumMessages:   2,
			NumExtensions: 0,
			NumServices:   1,
		},
		GoTypes:           file_messaging_proto_goTypes,
		DependencyIndexes: file_messaging_proto_depIdxs,
		EnumInfos:         file_messaging_proto_enumTypes,
		MessageInfos:      file_messaging_proto_msgTypes,
	}.Build()
	File_messaging_proto = out.File
	file_messaging_proto_rawDesc = nil
	file_messaging_proto_goTypes = nil
	file_messaging_proto_depIdxs = nil
}