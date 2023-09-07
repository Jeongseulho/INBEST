import StepPagination from "../molecules/StepPagination";
import StepPaginationDesc from "../molecules/StepPaginationDesc";

interface StepSideProps {
  curStep: number;
}

const StepSide = ({ curStep }: StepSideProps) => {
  const title = ["", "모의 투자", "입문자 친화", "다양한 투자 수단", "금융 상품과 비교", "랭킹 시스템"];
  const desc = [
    "",
    "가상으로 주어지는 시드머니로 부담없이 투자를 시작하세요",
    "친절한 튜토리얼, 도움말을 통해 쉽게 투자를 배울 수 있어요",
    "국내외 주식, 가상화폐 뿐만 아닌 금, 은과 같은 원자재 투자 까지",
    "내 투자 수익률과 금융 상품의 수익률을 비교해보세요",
    "모의 투자가 끝난 이후 결과로 랭킹을 확인할 수 있어요",
  ];

  return (
    <div className="fixed left-32  top-48 z-50 bg-transparent w-2/3">
      <StepPagination curStep={curStep} />
      <StepPaginationDesc title={title[curStep]} desc={desc[curStep]} />
    </div>
  );
};
export default StepSide;
