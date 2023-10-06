import { useIntro } from "./useIntro";
import money from "../../../asset/image/money.gif";
import home_phrases from "../../../asset/image/home_phrases.png";
interface IntroProps {
  setCurStep: React.Dispatch<React.SetStateAction<number>>;
}

const Intro = ({ setCurStep }: IntroProps) => {
  const { ref } = useIntro(setCurStep);
  return (
    <section ref={ref} className="flex flex-col items-center h-screen">
      <img src={home_phrases} alt="" width="800" />
      <div className=" mx-64 flex items-center mt-10">
        <img src={money} alt="" width="160" />
        <p className=" text-2xl font-regular text-gray-500">모의 투자를 통한 자산 관리 시뮬레이션을 제공합니다.</p>
      </div>
    </section>
  );
};
export default Intro;
