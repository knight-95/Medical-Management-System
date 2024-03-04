import { Component } from '@angular/core';

@Component({
  selector: 'app-events',
  standalone: true,
  imports: [],
  templateUrl: './events.component.html',
  styleUrl: './events.component.css'
})
export class EventsComponent {
  public name ="";
  // myEvent(event:any){
  //   console.log(event.isPrimary);
  // }
  myEvent(){
    console.log("double clicked");
  }
}
