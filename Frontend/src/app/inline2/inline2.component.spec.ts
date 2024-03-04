import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Inline2Component } from './inline2.component';

describe('Inline2Component', () => {
  let component: Inline2Component;
  let fixture: ComponentFixture<Inline2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Inline2Component]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(Inline2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
