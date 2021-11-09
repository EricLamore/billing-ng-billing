import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RatePlanChargeBillingDetailComponent } from './rate-plan-charge-billing-detail.component';

describe('RatePlanChargeBilling Management Detail Component', () => {
  let comp: RatePlanChargeBillingDetailComponent;
  let fixture: ComponentFixture<RatePlanChargeBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RatePlanChargeBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ratePlanCharge: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(RatePlanChargeBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RatePlanChargeBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ratePlanCharge on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ratePlanCharge).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
