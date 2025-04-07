import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelecterItemComponent } from './selecter-item.component';

describe('SelecterItemComponent', () => {
  let component: SelecterItemComponent;
  let fixture: ComponentFixture<SelecterItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelecterItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelecterItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
