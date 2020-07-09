import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MaxTravelledChartComponent } from './max-travelled-chart.component';

describe('MaxTravelledChartComponent', () => {
  let component: MaxTravelledChartComponent;
  let fixture: ComponentFixture<MaxTravelledChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MaxTravelledChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MaxTravelledChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
