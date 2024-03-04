import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UserComponent } from "./user/user.component";
import { InterpolationComponent } from "./interpolation/interpolation.component";
import { EventsComponent } from './events/events.component';
import { BindingsComponent } from "./bindings/bindings.component";
import { ClassBindingComponent } from "./class-binding/class-binding.component";
import { NgClassDirComponent } from "./ng-class-dir/ng-class-dir.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { Screen1Component } from './screen1/screen1.component';
import { FooterComponent } from './footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [RouterOutlet, UserComponent, InterpolationComponent, EventsComponent, BindingsComponent, ClassBindingComponent, NgClassDirComponent, NavbarComponent, Screen1Component, FooterComponent]
})
export class AppComponent {
  title = 'new-app';
}
