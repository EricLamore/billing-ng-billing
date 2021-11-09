import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductRatePlanBillingDetailComponent } from './product-rate-plan-billing-detail.component';

describe('ProductRatePlanBilling Management Detail Component', () => {
  let comp: ProductRatePlanBillingDetailComponent;
  let fixture: ComponentFixture<ProductRatePlanBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductRatePlanBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productRatePlan: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProductRatePlanBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductRatePlanBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productRatePlan on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productRatePlan).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
