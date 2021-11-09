jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { InvoiceItemBillingService } from '../service/invoice-item-billing.service';
import { IInvoiceItemBilling, InvoiceItemBilling } from '../invoice-item-billing.model';

import { InvoiceItemBillingUpdateComponent } from './invoice-item-billing-update.component';

describe('InvoiceItemBilling Management Update Component', () => {
  let comp: InvoiceItemBillingUpdateComponent;
  let fixture: ComponentFixture<InvoiceItemBillingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let invoiceItemService: InvoiceItemBillingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [InvoiceItemBillingUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(InvoiceItemBillingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InvoiceItemBillingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    invoiceItemService = TestBed.inject(InvoiceItemBillingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const invoiceItem: IInvoiceItemBilling = { id: 'CBA' };

      activatedRoute.data = of({ invoiceItem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(invoiceItem));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InvoiceItemBilling>>();
      const invoiceItem = { id: 'ABC' };
      jest.spyOn(invoiceItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoiceItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: invoiceItem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(invoiceItemService.update).toHaveBeenCalledWith(invoiceItem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InvoiceItemBilling>>();
      const invoiceItem = new InvoiceItemBilling();
      jest.spyOn(invoiceItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoiceItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: invoiceItem }));
      saveSubject.complete();

      // THEN
      expect(invoiceItemService.create).toHaveBeenCalledWith(invoiceItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<InvoiceItemBilling>>();
      const invoiceItem = { id: 'ABC' };
      jest.spyOn(invoiceItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ invoiceItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(invoiceItemService.update).toHaveBeenCalledWith(invoiceItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
