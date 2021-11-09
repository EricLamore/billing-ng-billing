import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubscriptionFeatureBillingDetailComponent } from './subscription-feature-billing-detail.component';

describe('SubscriptionFeatureBilling Management Detail Component', () => {
  let comp: SubscriptionFeatureBillingDetailComponent;
  let fixture: ComponentFixture<SubscriptionFeatureBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubscriptionFeatureBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ subscriptionFeature: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(SubscriptionFeatureBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubscriptionFeatureBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subscriptionFeature on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.subscriptionFeature).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
