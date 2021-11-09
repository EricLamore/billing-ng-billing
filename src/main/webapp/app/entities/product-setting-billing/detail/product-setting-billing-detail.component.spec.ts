import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductSettingBillingDetailComponent } from './product-setting-billing-detail.component';

describe('ProductSettingBilling Management Detail Component', () => {
  let comp: ProductSettingBillingDetailComponent;
  let fixture: ComponentFixture<ProductSettingBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductSettingBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ productSetting: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProductSettingBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductSettingBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load productSetting on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.productSetting).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
