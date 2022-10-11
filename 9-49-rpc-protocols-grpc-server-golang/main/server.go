package main

import (
	"context"
	"flag"
	"log"
	"net"

	"google.golang.org/grpc"
	pb "rntgroup.com/proto"
)

const (
    address = "localhost:9099"
    name = "GOLANG_SERVER"
)

type server struct {
	pb.UnimplementedMessagingServiceServer
}

func (s *server) SendMessage(ctx context.Context, in *pb.RequestMessage) (*pb.ResponseMessage, error) {
	log.Printf(name + ": received message \n '%v' \n from %v via %v", in.GetMessage(), in.GetSender(), in.GetType())

	response := &pb.ResponseMessage{}
	response.Type = in.GetType()
    response.Message = "Ok, ok, dear " + in.GetSender() + ", please, don't involve me into your mafia stuff"
	response.Sender = name
	response.Receiver = in.Sender

	return response, nil
}

func main() {
	flag.Parse()
	lis, err := net.Listen("tcp", ":9099")
	if err != nil {
		log.Fatalf("Failed to listen: %v", err)
	}

	s := grpc.NewServer()
	pb.RegisterMessagingServiceServer(s, &server{})
	log.Printf("Server listening at %v", lis.Addr())

	if err := s.Serve(lis); err != nil {
		log.Fatalf("Failed to serve: %v", err)
	}
}
