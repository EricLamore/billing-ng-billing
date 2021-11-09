jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { RatePlanBillingService } from '../service/rate-plan-billing.service';

import { RatePlanBillingDeleteDialogComponent } from './rate-plan-billing-delete-dialog.component';

describe('RatePlanBilling Management Delete Component', () => {
  let comp: RatePlanBillingDeleteDialogComponent;
  let fixture: ComponentFixture<RatePlanBillingDeleteDialogComponent>;
  let service: RatePlanBillingService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [RatePlanBillingDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(RatePlanBillingDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RatePlanBillingDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RatePlanBillingService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
