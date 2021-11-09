import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RatePlanBillingDetailComponent } from './rate-plan-billing-detail.component';

describe('RatePlanBilling Management Detail Component', () => {
  let comp: RatePlanBillingDetailComponent;
  let fixture: ComponentFixture<RatePlanBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RatePlanBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ ratePlan: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(RatePlanBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RatePlanBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load ratePlan on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.ratePlan).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
