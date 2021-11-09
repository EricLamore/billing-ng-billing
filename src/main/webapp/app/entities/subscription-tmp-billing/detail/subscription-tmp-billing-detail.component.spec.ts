import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SubscriptionTmpBillingDetailComponent } from './subscription-tmp-billing-detail.component';

describe('SubscriptionTmpBilling Management Detail Component', () => {
  let comp: SubscriptionTmpBillingDetailComponent;
  let fixture: ComponentFixture<SubscriptionTmpBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SubscriptionTmpBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ subscriptionTmp: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(SubscriptionTmpBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SubscriptionTmpBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load subscriptionTmp on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.subscriptionTmp).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
