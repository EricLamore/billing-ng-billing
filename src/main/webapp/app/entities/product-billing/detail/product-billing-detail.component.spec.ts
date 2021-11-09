import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProductBillingDetailComponent } from './product-billing-detail.component';

describe('ProductBilling Management Detail Component', () => {
  let comp: ProductBillingDetailComponent;
  let fixture: ComponentFixture<ProductBillingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductBillingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ product: { id: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(ProductBillingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProductBillingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load product on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.product).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });
});
