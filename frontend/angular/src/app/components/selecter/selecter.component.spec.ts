import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelecterComponent } from './selecter.component';

describe('SelecterComponent', () => {
  let component: SelecterComponent;
  let fixture: ComponentFixture<SelecterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelecterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelecterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
