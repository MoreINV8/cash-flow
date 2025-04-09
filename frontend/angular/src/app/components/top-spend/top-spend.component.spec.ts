import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopSpendComponent } from './top-spend.component';

describe('TopSpendComponent', () => {
  let component: TopSpendComponent;
  let fixture: ComponentFixture<TopSpendComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopSpendComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopSpendComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
