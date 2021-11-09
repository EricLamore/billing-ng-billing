import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FeatureBillingDetailComponent } from './feature-billing-detail.component';

describe('FeatureBilling Management Detail Component', () => {
  let comp: FeatureBillingDetailComponent;
  let fixture: ComponentFixture<FeatureBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FeatureBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ feature: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(FeatureBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(FeatureBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load feature on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.feature).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
