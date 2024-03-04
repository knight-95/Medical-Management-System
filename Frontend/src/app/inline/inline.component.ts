import { Component } from '@angular/core';

@Component({
  selector: 'app-inline',
  standalone: true,
  imports: [],
  // templateUrl: './inline.component.html',
  template:`<div> <h2>Yashu</h2> <p>This is from paragraph</p></div>`,
  // styleUrl: './inline.component.css'
  styles:`h2{
    color:green;
  }
  p{
    color:red;
    font-size:30px;
  }`
})
export class InlineComponent { }
