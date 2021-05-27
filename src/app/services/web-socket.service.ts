import { Injectable } from '@angular/core';
import { ChatMessageDto } from '../models/chatMessageDto';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  webSocket: WebSocket = new WebSocket("ws://localhost:9396/chat");
  chatMessage: ChatMessageDto[] = [];

  constructor() { }

  public openWebSocket(){
    this.webSocket = new WebSocket('ws://localhost:9396/chat');

    this.webSocket.onopen = (event) =>{
      console.log('Open: ', event)
    };

    this.webSocket.onmessage = (event) =>{
      const chatMessageDto = JSON.parse(event.data);
      this.chatMessage.push(chatMessageDto);
    };

    this.webSocket.onclose= (event) =>{
      console.log('Close: ', event);
    };
  }

  public sendMessage(chatMessageDto: ChatMessageDto){
    this.webSocket.send(JSON.stringify(chatMessageDto));
  }

  public closeWebSocket(){
    this.webSocket.close();
  }
}
