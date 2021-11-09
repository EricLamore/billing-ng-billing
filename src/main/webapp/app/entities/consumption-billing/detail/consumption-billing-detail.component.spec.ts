import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ConsumptionBillingDetailComponent } from './consumption-billing-detail.component';

describe('ConsumptionBilling Management Detail Component', () => {
  let comp: ConsumptionBillingDetailComponent;
  let fixture: ComponentFixture<ConsumptionBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConsumptionBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ consumption: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ConsumptionBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ConsumptionBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load consumption on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.consumption).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
