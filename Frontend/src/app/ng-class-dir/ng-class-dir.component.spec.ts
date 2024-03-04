import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgClassDirComponent } from './ng-class-dir.component';

describe('NgClassDirComponent', () => {
  let component: NgClassDirComponent;
  let fixture: ComponentFixture<NgClassDirComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NgClassDirComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NgClassDirComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
